package ru.shop.model;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final String phone;
    private final long age;

    public Customer(UUID id, String name, String phone, long age) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public long getAge() {
        return age;
    }
}
