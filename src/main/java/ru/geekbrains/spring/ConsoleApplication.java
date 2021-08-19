package ru.geekbrains.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.spring.config.ApplicationConfig;
import ru.geekbrains.spring.model.Cart;
import ru.geekbrains.spring.service.ProductService;

import java.util.Scanner;

public class ConsoleApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Cart cart = context.getBean(Cart.class);
        ProductService productService = context.getBean(ProductService.class);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            productService.printAll();
            showMenu();

            String command = scanner.nextLine();

            if (command.equals("add")) {
                System.out.println("Введите id товара");
                String idStr = scanner.nextLine();
                try {
                    cart.addProduct(Integer.parseInt(idStr));
                } catch (NumberFormatException e) {
                    System.out.println("id товара должен быть целым числом");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("В магазине отсутствует товар с указанным id");
                }
            } else if (command.equals("show")) {
                cart.showCart();
            } else if (command.equals("clear")) {
                System.out.println("clear");
                cart.clearCart();
            } else if (command.equals("exit")) {
                System.out.println("До свидания! Заходите к нам ещё!");
                return;
            } else {
                System.out.println("Неизвестная команда");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("[add] - добавить товар в корзину");
        System.out.println("[show] - показать содержимое корзины");
        System.out.println("[clear] - очистить корзину");
        System.out.println("[exit] - покинуть магазин\n");
    }
}
