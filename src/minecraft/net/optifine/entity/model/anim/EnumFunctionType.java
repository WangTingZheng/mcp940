package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;
import net.minecraft.src.MathUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public enum EnumFunctionType
{
    PLUS("+", 2, 0),
    MINUS("-", 2, 0),
    MUL("*", 2, 1),
    DIV("/", 2, 1),
    MOD("%", 2, 1),
    NEG("neg", 1),
    PI("pi", 0),
    SIN("sin", 1),
    COS("cos", 1),
    TAN("tan", 1),
    ATAN("atan", 1),
    ATAN2("atan2", 2),
    TORAD("torad", 1),
    TODEG("todeg", 1),
    MIN("min", 2),
    MAX("max", 2),
    CLAMP("clamp", 3),
    ABS("abs", 1),
    FLOOR("floor", 1),
    CEIL("ceil", 1),
    FRAC("frac", 1),
    ROUND("round", 1),
    SQRT("sqrt", 1),
    FMOD("fmod", 2),
    TIME("time", 0);

    private String name;
    private int countArguments;
    private int precedence;
    public static EnumFunctionType[] VALUES = values();

    private EnumFunctionType(String name, int countArguments)
    {
        this.name = name;
        this.countArguments = countArguments;
    }

    private EnumFunctionType(String name, int countArguments, int precedence)
    {
        this.name = name;
        this.countArguments = countArguments;
        this.precedence = precedence;
    }

    public String getName()
    {
        return this.name;
    }

    public int getCountArguments()
    {
        return this.countArguments;
    }

    public int getPrecedence()
    {
        return this.precedence;
    }

    public float eval(IExpression[] arguments)
    {
        if (arguments.length != this.countArguments)
        {
            Config.warn("Invalid number of arguments, function: " + this + ", arguments: " + arguments.length + ", should be: " + this.countArguments);
            return 0.0F;
        }
        else
        {
            switch (this)
            {
                case PLUS:
                    return arguments[0].eval() + arguments[1].eval();

                case MINUS:
                    return arguments[0].eval() - arguments[1].eval();

                case MUL:
                    return arguments[0].eval() * arguments[1].eval();

                case DIV:
                    return arguments[0].eval() / arguments[1].eval();

                case MOD:
                    float f = arguments[0].eval();
                    float f1 = arguments[1].eval();
                    return f - f1 * (float)((int)(f / f1));

                case NEG:
                    return -arguments[0].eval();

                case PI:
                    return (float)Math.PI;

                case SIN:
                    return MathHelper.sin(arguments[0].eval());

                case COS:
                    return MathHelper.cos(arguments[0].eval());

                case TAN:
                    return (float)Math.tan((double)arguments[0].eval());

                case ATAN:
                    return (float)Math.atan((double)arguments[0].eval());

                case ATAN2:
                    return (float)MathHelper.atan2((double)arguments[0].eval(), (double)arguments[1].eval());

                case TORAD:
                    return MathUtils.toRad(arguments[0].eval());

                case TODEG:
                    return MathUtils.toDeg(arguments[0].eval());

                case MIN:
                    return Math.min(arguments[0].eval(), arguments[1].eval());

                case MAX:
                    return Math.max(arguments[0].eval(), arguments[1].eval());

                case CLAMP:
                    return MathHelper.clamp(arguments[0].eval(), arguments[1].eval(), arguments[2].eval());

                case ABS:
                    return MathHelper.abs(arguments[0].eval());

                case FLOOR:
                    return (float)MathHelper.floor(arguments[0].eval());

                case CEIL:
                    return (float)MathHelper.ceil(arguments[0].eval());

                case FRAC:
                    return (float)MathHelper.frac((double)arguments[0].eval());

                case ROUND:
                    return (float)Math.round(arguments[0].eval());

                case SQRT:
                    return MathHelper.sqrt(arguments[0].eval());

                case FMOD:
                    float f2 = arguments[0].eval();
                    float f3 = arguments[1].eval();
                    return f2 - f3 * (float)MathHelper.floor(f2 / f3);

                case TIME:
                    Minecraft minecraft = Minecraft.getMinecraft();
                    World world = minecraft.world;

                    if (world == null)
                    {
                        return 0.0F;
                    }

                    return (float)(world.getTotalWorldTime() % 24000L) + minecraft.getRenderPartialTicks();

                default:
                    Config.warn("Unknown function type: " + this);
                    return 0.0F;
            }
        }
    }

    public static EnumFunctionType parse(String str)
    {
        for (int i = 0; i < VALUES.length; ++i)
        {
            EnumFunctionType enumfunctiontype = VALUES[i];

            if (enumfunctiontype.getName().equals(str))
            {
                return enumfunctiontype;
            }
        }

        return null;
    }
}
