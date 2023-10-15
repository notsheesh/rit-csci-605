/*
 * Problem Description: 
 * Suppose you are developing a multimedia application that supports 
 * various types of media files, such as images, videos, and audio. You want 
 * to create a system for processing and rendering these media files. Each 
 * media file type requires a different set of methods and properties for 
 * processing and rendering.
 * 
 * Using an Abstract Class for Multimedia Application:
 * In the multimedia application scenario, where we have different types of 
 * media files (e.g., images, videos, audio), using an abstract class would not 
 * be the best choice because:
 * 
 * 1. Lack of Multiple Inheritance: In many programming languages like Java, a 
 * class can extend only one abstract class, but it can implement multiple 
 * interfaces. If you were to use an abstract class for media files, it would 
 * limit the flexibility to inherit from other classes, potentially causing 
 * issues when you need to model media files with additional characteristics or 
 * behaviors that are not related to media processing. 
 * 
 * 2. Limited Code Reuse: Abstract classes allow you to provide some default 
 * implementations, but they don't allow for the reuse of code across unrelated 
 * classes. If there are media-related functionalities that need to be shared 
 * among different media types (e.g., a common library for compression 
 * algorithms), abstract classes would not facilitate this code sharing 
 * effectively.
 * 
 * Why an Interface is More Appropriate:
 * In this scenario, using an interface would be a better choice than an 
 * abstract class. The reason is that media file types like images, videos, and
 * audio may have fundamentally different characteristics and behavior, and you 
 * want to support multiple inheritance. An interface allows you to define a 
 * contract that each media file type must implement, specifying the required 
 * methods and properties.
 * 
 * In this example, we use an interface IMediaFile to define a contract for 
 * processing and rendering media files. Each specific media file type (e.g., 
 * Image, Video, Audio) implements this interface, providing their own 
 * implementations for the process() and render() methods. This design allows 
 * flexibility and ensures that each media type can have its unique behavior.
 * 
 * * Author: Shreesh Tripathi, Kyle Burke 
 * 
*/

// Interface for processing and rendering media files
interface IMediaFile {
    void process();
    void render();
}

// Specific media file types using the interface
class Image implements IMediaFile {
    @Override
    public void process() {
        // Image processing logic
    }

    @Override
    public void render() {
        // Image rendering logic
    }
}

class Video implements IMediaFile {
    @Override
    public void process() {
        // Video processing logic
    }

    @Override
    public void render() {
        // Video rendering logic
    }
}

class Audio implements IMediaFile {
    @Override
    public void process() {
        // Audio processing logic
    }

    @Override
    public void render() {
        // Audio rendering logic
    }
}

public class MultimediaAppInterface {
    public static void main(String[] args) {
        // Different types of media files
        IMediaFile image = new Image();
        IMediaFile video = new Video();
        IMediaFile audio = new Audio();

        image.process();
        image.render();

        video.process();
        video.render();

        audio.process();
        audio.render();
    }
}
