import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class World {

    // The window handle
    private long window;

    private final int windowWidth = 1200;
    private final int windowHeight = 800;

    ImageTexture imageTexture = new ImageTexture();

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Cleanup
        imageTexture.deleteTexture();
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

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
        imageTexture.loadTexture("ant.png");
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

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // Clear the screen
            glClear(GL_COLOR_BUFFER_BIT);

            // Draw the texture
            imageTexture.drawTexture(0, 0);
            imageTexture.drawTexture(10, 10);
            imageTexture.drawTexture(20, 20);
            imageTexture.drawTexture(30, 30);
            imageTexture.drawTexture(40, 40);

            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new World().run();
    }
}