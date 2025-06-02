package adamyan;

import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.*;

public class ImageTexture {

    // Texture variables
    public int textureId;
    private int imageWidth;
    private int imageHeight;

    // Image size on screen
    private final float imageDisplayScale = 1.0f;

    // Image size on screen
    private float imageDisplayWidth = 0.0f;
    private float imageDisplayHeight = 0.0f;

    public void loadTexture(String filepath) {
        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            // Load image data
            ByteBuffer imageData = stbi_load(filepath, w, h, channels, 4); // Force RGBA

            imageWidth = w.get();
            imageHeight = h.get();

            imageDisplayWidth = imageWidth * imageDisplayScale;
            imageDisplayHeight = imageHeight * imageDisplayScale;

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

    public void drawTexture(int x, int y) {
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Set white color (no tinting)
        glColor3f(1.0f, 1.0f, 1.0f);

        // Draw a textured quad
        glBegin(GL_QUADS);
        // Bottom-left
        glTexCoord2f(0.0f, 1.0f);
        glVertex2f(x, imageDisplayHeight + y);

        // Bottom-right
        glTexCoord2f(1.0f, 1.0f);
        glVertex2f(imageDisplayWidth + x, imageDisplayHeight + y);

        // Top-right
        glTexCoord2f(1.0f, 0.0f);
        glVertex2f(imageDisplayWidth + x, y);

        // Top-left
        glTexCoord2f(0.0f, 0.0f);
        glVertex2f(x, y);
        glEnd();
    }

    public void deleteTexture() {
        glDeleteTextures(textureId);
    }
}
