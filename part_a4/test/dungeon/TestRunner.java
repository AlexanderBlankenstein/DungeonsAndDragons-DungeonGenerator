package dungeon;

import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String [] args) {

        TestRunner testRunner = new TestRunner();
        testRunner.runTest("Chamber", ChamberTest.class);
        testRunner.runTest("Door", DoorTest.class);
        testRunner.runTest("Passage", PassageTest.class);
        testRunner.runTest("PassageSectionTest", PassageSectionTest.class);
	}

	private void runTest(String name, Class testClass){
        System.out.println("==============="+name+"Test===================");
        Result result = JUnitCore.runClasses(testClass);
        System.out.println("\n*****Failed Test Report****\n");
        List<Failure> failedList = result.getFailures();
        failedList.forEach(f -> {System.out.println(f);});
        System.out.println("Number of Failed Tests = " + result.getFailureCount());
    }
}