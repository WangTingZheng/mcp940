package shadersmod.client;

import org.lwjgl.opengl.ARBShaderObjects;

public abstract class ShaderUniformBase
{
    private String name;
    private int program = -1;
    private int location = -1;

    public ShaderUniformBase(String name)
    {
        this.name = name;
    }

    public void setProgram(int program)
    {
        if (this.program != program)
        {
            this.program = program;
            this.location = ARBShaderObjects.glGetUniformLocationARB(program, this.name);
            this.onProgramChanged();
        }
    }

    protected abstract void onProgramChanged();

    public String getName()
    {
        return this.name;
    }

    public int getProgram()
    {
        return this.program;
    }

    public int getLocation()
    {
        return this.location;
    }

    public boolean isDefined()
    {
        return this.location >= 0;
    }
}
