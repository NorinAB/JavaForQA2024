package ru.shop;

public class Main {
    public static void main(String[] args) {

        ProductService productService = new ProductService(new ProductRepository());

        CustomerService customerService = new CustomerService(new CustomerRepository());

        OrderService orderService = new OrderService(new OrderRepository());


        productService.save(new Product("prod_id_0", "Prod_0", 50, ProductType.GOOD));
        productService.save(new Product("prod_id_1", "Prod_1", 100, ProductType.SERVICE));

        customerService.save(new Customer("cust_id_0", "Cust_0", "8080", 20));
        customerService.save(new Customer("cust_id_1", "Cust_1", "5050", 25));

        try {
            orderService.add(customerService.findAll().get(0), productService.findAll().get(0), 2);
            orderService.add(customerService.findAll().get(0), productService.findAll().get(1), 1);
            orderService.add(customerService.findAll().get(0), productService.findAll().get(1), -1);

        } catch (BadOrderCountException e) {
            e.printStackTrace();
        }

        System.out.println("Количество заказчиков: " + customerService.findAll().size());

        System.out.println("Количество заказов всего: " + orderService.findAll().size());

        System.out.println("Количество заказов по типам: ");
        for (ProductType type : ProductType.values()) {
            System.out.println("  " + type + ": " + productService.findByProductType(type).size());
        }

        System.out.println("Количество заказов по заказчикам: ");
        for (Customer customer : customerService.findAll()) {
            System.out.println("  " + customer.name() + ": " + orderService.findByCustomer(customer).size());
        }

        System.out.println("Суммы для оплаты по заказчикам: ");
        for (Customer customer : customerService.findAll()) {
            System.out.println("  " + customer.name() + ": " + orderService.getTotalCustomerAmount(customer));
        }
    }
}
