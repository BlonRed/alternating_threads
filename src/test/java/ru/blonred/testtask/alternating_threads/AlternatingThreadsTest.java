package ru.blonred.testtask.alternating_threads;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlternatingThreadsTest {

	private ByteArrayOutputStream outputStream;
	private static WriteAndRead writeAndRead;

	void recordTextFromConsole() {
		outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
	}
	private void closeRecordFromConsole() {
		try {
			outputStream.close();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	@BeforeEach
	void prepare(){
		writeAndRead = new WriteAndRead();
	}

	@Test
	void test1Run() {
		recordTextFromConsole();
		int number = 2;
		String expectedOutput = "Old value: " + 0 + "\n" + "New value: " + 1 + "\n" + "Thread #1" + "\n"  + "\n" +
				"Old value: " + 1 + "\n" + "New value: " + 2 + "\n" + "Thread #2" + "\n" + "\n";

		ThreadWriteAndRead twrob1 = new ThreadWriteAndRead("Thread #1", number, writeAndRead);
		ThreadWriteAndRead twrob2 = new ThreadWriteAndRead("Thread #2", number, writeAndRead);
		try {
			twrob1.thread.join();
			twrob2.thread.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}
		closeRecordFromConsole();

		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void test2Run() {
		int number = 2;
		ThreadWriteAndRead twrob1 = new ThreadWriteAndRead("Thread #1", number, writeAndRead);
		ThreadWriteAndRead twrob2 = new ThreadWriteAndRead("Thread #2", number, writeAndRead);
		try {
			twrob1.thread.join();
			twrob2.thread.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}

		assertEquals("2", writeAndRead.readContent());
	}

	@Test
	void test3Run() {
		int number = 1;
		ThreadWriteAndRead twrob1 = new ThreadWriteAndRead("Thread #1", number, writeAndRead);
		ThreadWriteAndRead twrob2 = new ThreadWriteAndRead("Thread #2", number, writeAndRead);
		try {
			twrob1.thread.join();
			twrob2.thread.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}

		assertEquals("1", writeAndRead.readContent());
	}

	@Test
	void test4Run() {
		int number = 4;
		ThreadWriteAndRead twrob1 = new ThreadWriteAndRead("Thread #1", number, writeAndRead);

		assertEquals("0", writeAndRead.readContent());
	}

	@Test
	void testGetPathToResources() {
		String path = AlternatingThreads.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
		String[] TestSplit = decodedPath.split("alternating_threads");
		String test = TestSplit[0];
		test = test.replace("/", "\\");

		String[] splitGet = writeAndRead.getPathToResources().split("alternating_threads");
		String testGet = splitGet[0];

		assertEquals(test, testGet);
	}

	@Test
	void testReadContent() {
		assertEquals("0", writeAndRead.readContent());
	}

	@Test
	void testWriteContent() {
		writeAndRead.writeContent(256);
		assertEquals("256", writeAndRead.readContent());
	}

	@Test
	void testThreadOneAndThreadTwo() {
		recordTextFromConsole();
		int number = 4;
		String expectedOutput = "Old value: " + 0 + "\n" + "New value: " + 1 + "\n" + "Thread #1" + "\n" + "\n" +
				"Old value: " + 1 + "\n" + "New value: " + 2 + "\n" + "Thread #2" + "\n" + "\n" +
				"Old value: " + 2 + "\n" + "New value: " + 3 + "\n" + "Thread #1" + "\n" + "\n" +
				"Old value: " + 3 + "\n" + "New value: " + 4 + "\n" + "Thread #2" + "\n" + "\n";

		ThreadWriteAndRead twrob1 = new ThreadWriteAndRead("Thread #1", number, writeAndRead);
		ThreadWriteAndRead twrob2 = new ThreadWriteAndRead("Thread #2", number, writeAndRead);
		try {
			twrob1.thread.join();
			twrob2.thread.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}
		closeRecordFromConsole();

		assertEquals(expectedOutput, outputStream.toString());
	}
}
