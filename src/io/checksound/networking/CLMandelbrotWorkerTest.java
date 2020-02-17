package io.checksound.networking;

import java.util.Locale;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class CLMandelbrotWorkerTest {

	@Test
	void test() {
		String taskData = "task 0 10000 0.25254192934972913 -0.9548900066789311 3.9965434972262344E-10 1024";
		Scanner scanner = new Scanner(taskData);
		scanner.useLocale(Locale.US);
		
        CLMandelbrotTask task = new CLMandelbrotTask();
        scanner.next();  // skip the command at the start of the line.
        task.id = scanner.nextInt();
        task.maxIterations = scanner.nextInt();
        
        task.y = scanner.nextDouble();
        task.xmin = scanner.nextDouble();
        task.dx = scanner.nextDouble();
        task.count = scanner.nextInt();
        
        scanner.close();
	}

}
