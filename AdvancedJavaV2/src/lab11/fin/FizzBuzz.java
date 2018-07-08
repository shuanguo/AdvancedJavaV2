package lab11.fin;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lab.util.BiHolder;

public class FizzBuzz
{
	private static String divisibleOrNot(int divisibleInt, int modulo, Supplier<String> ifDivisible, Supplier<String> ifNotDivisible) {
		return Math.floorMod(divisibleInt, modulo) == 0 ? ifDivisible.get() : ifNotDivisible.get();
	}
	
	private static List<String> getFizzBuzzList(int start, int end) {

		return IntStream.rangeClosed(start, end).
        		mapToObj(i -> new BiHolder<Integer, String>(i, "")).
        		map(h -> h.setU(divisibleOrNot(h.getT(), 3, () -> h.getU() + "Fizz", () -> h.getU()))).
        		map(h -> h.setU(divisibleOrNot(h.getT(), 5, () -> h.getU() + "Buzz", () -> h.getU()))).
        		filter(h -> h.getU().length() > 0).
        		map(h -> h.getT() + " " + h.getU()).
        		collect(Collectors.toList());
	}

	private static List<String> getFizzBuzzListWithCurrying(int start, int end) {
		
		// Using currying to define the modulo function once only.
		BiFunction<String, Integer, UnaryOperator<BiHolder<Integer, String>>> moduloFunction = 
				(fizzBuzz, modulo) -> 
					h -> h.setU(Math.floorMod(h.getT(), modulo) == 0 ? h.getU() + fizzBuzz : h.getU());
		
        return IntStream.rangeClosed(start, end).
        		mapToObj(i -> new BiHolder<Integer, String>(i, "")).
        		map(h -> moduloFunction.apply("Fizz", 3).apply(h)).
        		map(h -> moduloFunction.apply("Buzz", 5).apply(h)).
        		filter(h -> h.getU().length() > 0).
        		map(h -> h.getT() + " " + h.getU()).
        		collect(Collectors.toList());
	}
	
    public static void main(String... args)
    {
    	getFizzBuzzList(1, 100).forEach(System.out::println);
    	getFizzBuzzListWithCurrying(1, 100).forEach(System.out::println);
    }
}
