package lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Test
{
	public static void main(String[] args) {
	    System.out.println(warningUnnecessarySuppressWarnings());
	    System.out.println(warningUnsafeCast());
	    System.out.println(withoutWarning());
	}

	private static Integer perform(Function<List<?>, Integer> func) {
	    return func.apply(Arrays.asList("a", "b", "c"));
	}

	private static Integer warningUnnecessarySuppressWarnings() {
	    return perform(list -> {
	        @SuppressWarnings("unchecked") // Unnecessary @SuppressWarnings("unchecked")
	        List<String> unsafeCast = (List<String>) list;
	        return unsafeCast.size();
	    });
	}

	private static Integer warningUnsafeCast() {
	    return perform(list -> {
	        List<String> unsafeCast = (List<String>) list; // Type safety: Unchecked cast from List<capture#4-of ?> to List<String>
	        return unsafeCast.size();
	    });
	}

	@SuppressWarnings("unchecked")
	private static Integer withoutWarning() {
	    return perform(list -> {
	        List<String> unsafeCast = (List<String>) list;
	        return unsafeCast.size();
	    });
	}
}
