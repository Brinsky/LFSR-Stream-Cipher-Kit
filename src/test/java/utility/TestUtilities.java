package utility;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/** Utility methods to reduce redundant test code. */
public class TestUtilities {

	/** Creates a mock for an abstract class with constructor arguments.
	 * 
	 * @param classToMock The abstract class to be mocked.
	 * @param constructorArgs A sequence of arguments to be passed to the
	 * 	abstract class' constructor.
	 * @return A newly created mock instance of the abstract class.
	 */
	public static <T> T mockAbstractClass(Class<T> classToMock,
			Object ... constructorArgs) {
		
		return mock(classToMock, withSettings().useConstructor(constructorArgs)
				.defaultAnswer(CALLS_REAL_METHODS));
	}
}
