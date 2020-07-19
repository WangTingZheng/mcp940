package net.minecraftforge.common.model;

import java.util.Optional;

public interface IModelState
{
    Optional<TRSRTransformation> apply(Optional <? extends IModelPart > var1);
}
