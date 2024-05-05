package com.stuti.database.mappers;

public interface Mapper<A, B> {

    B mapto(A a);

    A mapfrom(B b);
}
