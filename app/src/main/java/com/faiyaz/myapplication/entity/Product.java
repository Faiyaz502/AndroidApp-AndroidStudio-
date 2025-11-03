package com.faiyaz.myapplication.entity;

public class Product {

        private int id ;
        private String name ;
        private String email ;

        private Double price ;

        private Integer quantity ;

        public Product(String name, String email, Integer quantity, Double price) {

                this.name = name;
                this.email = email;
                this.quantity = quantity;
                this.price = price;
        }

        public Product(int id, String name, String email, Double price, Integer quantity) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.price = price;
                this.quantity = quantity;
        }

        public Product() {

        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Integer getQuantity() {
                return quantity;
        }

        public void setQuantity(Integer quantity) {
                this.quantity = quantity;
        }
}
