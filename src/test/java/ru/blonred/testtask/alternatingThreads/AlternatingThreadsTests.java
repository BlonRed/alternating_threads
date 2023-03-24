package ru.blonred.testtask.alternatingThreads;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlternatingThreadsTests {

	final String s = System.lineSeparator();

	@Test
	void test1Run() {
		WriteAndRead wr = new WriteAndRead();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		int number = 2;
		String expectedOutput = "Old value: " + 0 + "\n" + "New value: " + 1 + "\n" + "Thread #1" + "\n" + s +
				"Old value: " + 1 + "\n" + "New value: " + 2 + "\n" + "Thread #2" + "\n" + s;

		ThreadWR twrob1 = new ThreadWR("Thread #1", number, wr);
		ThreadWR twrob2 = new ThreadWR("Thread #2", number, wr);
		try {
			twrob1.thrd.join();
			twrob2.thrd.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}

		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void test2Run() {
		WriteAndRead wr = new WriteAndRead();
		int number = 2;

		ThreadWR twrob1 = new ThreadWR("Thread #1", number, wr);
		ThreadWR twrob2 = new ThreadWR("Thread #2", number, wr);
		try {
			twrob1.thrd.join();
			twrob2.thrd.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}

		assertEquals("2", wr.readContent());
	}

	@Test
	void test3Run() {
		WriteAndRead wr = new WriteAndRead();
		int number = 1;

		ThreadWR twrob1 = new ThreadWR("Thread #1", number, wr);
		ThreadWR twrob2 = new ThreadWR("Thread #2", number, wr);
		try {
			twrob1.thrd.join();
			twrob2.thrd.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}

		assertEquals("0", wr.readContent());
	}

	@Test
	void test4Run() {
		WriteAndRead wr = new WriteAndRead();
		int number = 4;

		ThreadWR twrob1 = new ThreadWR("Thread #1", number, wr);

		assertEquals("0", wr.readContent());
	}

	@Test
	void testGetPathToResources() {
		WriteAndRead wr = new WriteAndRead();
		String path = AlternatingThreads.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
		String[] TestSplit = decodedPath.split("alternatingThreads");
		String test = TestSplit[0];
		test = test.replace("/", "\\");

		String[] splitGet = wr.getPathToResources().split("alternatingThreads");
		String testGet = splitGet[0];

		assertEquals(test, testGet);
	}

	@Test
	void testReadContent() {
		WriteAndRead wr = new WriteAndRead();
		assertEquals("0", wr.readContent());
	}

	@Test
	void testWriteContent() {
		WriteAndRead wr = new WriteAndRead();
		wr.writeContent(256);
		assertEquals("256", wr.readContent());
	}

	@Test
	void testThreadOneAndThreadTwo() {
		WriteAndRead wr = new WriteAndRead();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		int number = 4;
		String expectedOutput = "Old value: " + 0 + "\n" + "New value: " + 1 + "\n" + "Thread #1" + "\n" + s +
				"Old value: " + 1 + "\n" + "New value: " + 2 + "\n" + "Thread #2" + "\n" + s +
				"Old value: " + 2 + "\n" + "New value: " + 3 + "\n" + "Thread #1" + "\n" + s +
				"Old value: " + 3 + "\n" + "New value: " + 4 + "\n" + "Thread #2" + "\n" + s;

		ThreadWR twrob1 = new ThreadWR("Thread #1", number, wr);
		ThreadWR twrob2 = new ThreadWR("Thread #2", number, wr);
		try {
			twrob1.thrd.join();
			twrob2.thrd.join();
		} catch (InterruptedException exc) {
			System.out.println("Main thread interruption");
		}

		assertEquals(expectedOutput, outputStream.toString());
	}
}
