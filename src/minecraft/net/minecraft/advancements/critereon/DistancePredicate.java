package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;

public class DistancePredicate
{
    /** The predicate that matches any distance. */
    public static final DistancePredicate ANY = new DistancePredicate(MinMaxBounds.UNBOUNDED, MinMaxBounds.UNBOUNDED, MinMaxBounds.UNBOUNDED, MinMaxBounds.UNBOUNDED, MinMaxBounds.UNBOUNDED);
    private final MinMaxBounds x;
    private final MinMaxBounds y;
    private final MinMaxBounds z;
    private final MinMaxBounds horizontal;
    private final MinMaxBounds absolute;

    public DistancePredicate(MinMaxBounds x, MinMaxBounds y, MinMaxBounds z, MinMaxBounds horizontal, MinMaxBounds absolute)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.horizontal = horizontal;
        this.absolute = absolute;
    }

    public boolean test(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        float f = (float)(x1 - x2);
        float f1 = (float)(y1 - y2);
        float f2 = (float)(z1 - z2);

        if (this.x.test(MathHelper.abs(f)) && this.y.test(MathHelper.abs(f1)) && this.z.test(MathHelper.abs(f2)))
        {
            if (!this.horizontal.testSquare((double)(f * f + f2 * f2)))
            {
                return false;
            }
            else
            {
                return this.absolute.testSquare((double)(f * f + f1 * f1 + f2 * f2));
            }
        }
        else
        {
            return false;
        }
    }

    public static DistancePredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "distance");
            MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(jsonobject.get("x"));
            MinMaxBounds minmaxbounds1 = MinMaxBounds.deserialize(jsonobject.get("y"));
            MinMaxBounds minmaxbounds2 = MinMaxBounds.deserialize(jsonobject.get("z"));
            MinMaxBounds minmaxbounds3 = MinMaxBounds.deserialize(jsonobject.get("horizontal"));
            MinMaxBounds minmaxbounds4 = MinMaxBounds.deserialize(jsonobject.get("absolute"));
            return new DistancePredicate(minmaxbounds, minmaxbounds1, minmaxbounds2, minmaxbounds3, minmaxbounds4);
        }
        else
        {
            return ANY;
        }
    }
}
