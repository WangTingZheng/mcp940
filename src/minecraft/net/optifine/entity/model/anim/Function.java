package net.optifine.entity.model.anim;

public class Function implements IExpression
{
    private EnumFunctionType enumFunction;
    private IExpression[] arguments;

    public Function(EnumFunctionType enumFunction, IExpression[] arguments)
    {
        this.enumFunction = enumFunction;
        this.arguments = arguments;
    }

    public float eval()
    {
        return this.enumFunction.eval(this.arguments);
    }

    public String toString()
    {
        return "" + this.enumFunction + "()";
    }
}
