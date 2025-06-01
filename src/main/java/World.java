import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class World {

    // The window handle
    private long window;

    private final int windowWidth = 1200;
    private final int windowHeight = 800;

    // Texture variables
    private int textureId;
    private int imageWidth;
    private int imageHeight;

    // Position variables for animation
    private float x = 100.0f;
    private float y = 100.0f;
    private float velocityX = 2.0f;
    private float velocityY = 1.5f;

    // Image size on screen
    private final float imageDisplayWidth = 8.0f;
    private final float imageDisplayHeight = 8.0f;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Cleanup
        glDeleteTextures(textureId);
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(windowWidth, windowHeight, "Moving PNG Texture", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup key callback
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        // Center the window
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable v-sync
        glfwShowWindow(window);

        // Initialize OpenGL
        GL.createCapabilities();

        // Setup 2D rendering
        setupOpenGL();

        // Load texture
        loadTexture("ant.png"); // Replace with your PNG file path
    }

    private void setupOpenGL() {
        // Enable 2D texturing
        glEnable(GL_TEXTURE_2D);

        // Enable blending for transparency
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Set up orthographic projection for 2D rendering
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, windowWidth, windowHeight, 0, -1, 1); // Origin at top-left

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Set clear color to light gray
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void loadTexture(String filepath) {
        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            // Load image data
            ByteBuffer imageData = stbi_load(filepath, w, h, channels, 4); // Force RGBA

            imageWidth = w.get();
            imageHeight = h.get();

            // Generate and bind texture
            textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);

            // Set texture parameters
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            // Upload texture data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);

            // Free image data
            stbi_image_free(imageData);

            System.out.println("Loaded texture: " + filepath + " (" + imageWidth + "x" + imageHeight + ")");
        }
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // Update position
            updatePosition();

            // Clear the screen
            glClear(GL_COLOR_BUFFER_BIT);

            // Draw the texture
            drawTexture();

            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void updatePosition() {
        // Update position
        x += velocityX;
        y += velocityY;

        // Bounce off edges
        if (x <= 0 || x >= windowWidth - imageDisplayWidth) {
            velocityX = -velocityX;
            x = Math.max(0, Math.min(x, windowWidth - imageDisplayWidth));
        }

        if (y <= 0 || y >= windowHeight - imageDisplayHeight) {
            velocityY = -velocityY;
            y = Math.max(0, Math.min(y, windowHeight - imageDisplayHeight));
        }
    }

    private void drawTexture() {
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Set white color (no tinting)
        glColor3f(1.0f, 1.0f, 1.0f);

        // Draw a textured quad
        glBegin(GL_QUADS);
        // Bottom-left
        glTexCoord2f(0.0f, 1.0f);
        glVertex2f(x, y + imageDisplayHeight);

        // Bottom-right
        glTexCoord2f(1.0f, 1.0f);
        glVertex2f(x + imageDisplayWidth, y + imageDisplayHeight);

        // Top-right
        glTexCoord2f(1.0f, 0.0f);
        glVertex2f(x + imageDisplayWidth, y);

        // Top-left
        glTexCoord2f(0.0f, 0.0f);
        glVertex2f(x, y);
        glEnd();
    }

    public static void main(String[] args) {
        new World().run();
    }
}