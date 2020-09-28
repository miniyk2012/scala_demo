class Fruit {
}

class Banana extends Fruit {
}

class Apple extends Fruit {
}

public class Trouble {
    public static void main(String[] args) {
        Banana[] bananas = new Banana[2];
        bananas[0] = new Banana();

        Object[] fruits = bananas;
        fruits[1] = new Apple();  // 运行时报错

        for (Banana banana: bananas) {
            System.out.println(banana);
        }
    }
}
