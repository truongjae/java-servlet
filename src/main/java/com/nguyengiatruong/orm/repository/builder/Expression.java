package com.nguyengiatruong.orm.repository.builder;

public interface Expression {
    String expression();
    static String equal(){
        return " = ";
    }
    static String notEqual(){
        return "<>";
    }
    static String gt(){
        return ">";
    }
    static String gte(){
        return ">=";
    }
    static String like(){
        return " LIKE ";
    }
    static String lt(){
        return "<";
    }
    static String lte(){
        return "<=";
    }
    static String between(){
        return "BETWEEN";
    }
    static String in(){
        return "in";
    }
    static String isNULL(){
        return "is null";
    }
    static String isNOTNULL() {
        return "is not null";
    }
    static String and(){
        return " AND ";
    }
    static String or(){
        return " OR ";
    }
}
