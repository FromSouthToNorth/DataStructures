package vip.hyzt.bag;

import org.junit.jupiter.api.Test;

public class BagTest {

    @Test
    void bagTest() {
        Bag<String> bags = new Bag<>();
        bags.add("1");
        bags.add("2");
        bags.add("3");

        System.out.println("size fo bags = " + bags.size());

        for (String bag : bags) {
            System.out.println(bag);
        }

        System.out.println(bags.contains(null));
        System.out.println(bags.contains("1"));
        System.out.println(bags.contains("3"));
    }

}
