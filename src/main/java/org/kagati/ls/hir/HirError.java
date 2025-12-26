package org.kagati.ls.hir;

import org.kagati.ls.span.SourceSpan;

public interface HirError {
    SourceSpan span();
    String message();
}