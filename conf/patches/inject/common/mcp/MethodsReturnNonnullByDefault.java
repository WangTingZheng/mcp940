package mcp;
import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.meta.TypeQualifierDefault;

/**
 * This annotation can be applied to a package, class or method to indicate that
 * the method in that element are nonnull by default unless there is:
 * <ul>
 * <li>An explicit nullness annotation
 * <li>The method overrides a method in a superclass (in which case the
 * annotation of the corresponding method in the superclass applies)
 * <li> there is a default parameter annotation applied to a more tightly nested
 * element.
 * </ul>
 *
 */
@Documented
@Nonnull
@TypeQualifierDefault(ElementType.METHOD) // Note: This is a copy of javax.annotation.ParametersAreNonnullByDefault with target changed to METHOD
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodsReturnNonnullByDefault {}