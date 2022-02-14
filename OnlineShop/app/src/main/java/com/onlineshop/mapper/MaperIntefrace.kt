package com.onlineshop.mapper

/**
 * Interface for in-project mappers
 *
 * @param A type of first of objects to transform
 * @param B type of second of objects to transform
 */
interface MapperInterface<A, B> {
    /**
     * Transforms object to main type of project
     *
     * @param type type of input
     *
     * @return object of class B, transformed from input
     */
    fun transformToSecondType(type: A): B

    /**
     * Transforms object to transfer type
     *
     * @param type type of input
     *
     * @return object of class A, transformed from input
     */
    fun transformToFirstType(type: B): A
}
