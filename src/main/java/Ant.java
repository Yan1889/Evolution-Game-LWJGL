import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.*;

public class Ant {

    // Texture variables
    public int textureId;
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

    public void loadTexture(String filepath) {
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

    public void updatePosition() {
        // Update position
        x += velocityX;
        y += velocityY;

        // Bounce off edges
        if (x <= 0 || x >= 1200 - imageDisplayWidth) {
            velocityX = -velocityX;
            x = Math.max(0, Math.min(x, 1200 - imageDisplayWidth));
        }

        if (y <= 0 || y >= 800 - imageDisplayHeight) {
            velocityY = -velocityY;
            y = Math.max(0, Math.min(y, 800 - imageDisplayHeight));
        }
    }


    public void drawTexture() {
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


}
