package org.kagati.ls.hir.type;

import org.kagati.ls.hir.HirError;
import org.kagati.ls.span.SourceSpan;

public final class HirTypeError implements HirError {
    private SourceSpan span;
    private String message;

    public HirTypeError(SourceSpan span, String message) {
        this.span = span;
        this.message = message;
    }

    @Override
    public SourceSpan span() {
        return span;
    }

    @Override
    public String message() {
        return message;
    }
}