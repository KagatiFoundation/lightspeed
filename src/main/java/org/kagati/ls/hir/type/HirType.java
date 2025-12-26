package org.kagati.ls.hir.type;

public sealed interface HirType permits IntType, BoolType, ErrorType {
    int sizeInBytes();
}

final class IntType implements HirType {
    public static final IntType INSTANCE = new IntType();
    public static final int SIZE_IN_BYTES = 8;

    private IntType() { }

    public int sizeInBytes() {
        return SIZE_IN_BYTES;
    }

    @Override
    public String toString() {
        return "int";
    }
}

final class BoolType implements HirType {
    public static final BoolType INSTANCE = new BoolType();
    public static final int SIZE_IN_BYTES = 1;

    private BoolType() { }

    public int sizeInBytes() {
        return SIZE_IN_BYTES;
    }

    @Override
    public String toString() {
        return "bool";
    }
}

final class ErrorType implements HirType {
    public static final ErrorType INSTANCE = new ErrorType();
    public static final int SIZE_IN_BYTES = 1;

    private ErrorType() { }

    public int sizeInBytes() {
        return SIZE_IN_BYTES;
    }

    @Override
    public String toString() {
        return "error";
    }
}