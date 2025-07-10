
import java.io.IOException;
import java.nio.file.*;

public class HandlingFileIO {
	public static void main(String[] args) throws IOException {
		// create new file and write to it. then stream each line to the console.
		Path path = Files.writeString(Path.of("./dst/newFile.txt"), "Hehe\r\nHaha\r\n", StandardOpenOption.CREATE);
		Files.lines(path)
				.forEach(System.out::println);
	}
}
