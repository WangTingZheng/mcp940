package shadersmod.client;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniformFloat4 extends ShaderUniformBase
{
    private float[] values = new float[4];

    public ShaderUniformFloat4(String name)
    {
        super(name);
    }

    protected void onProgramChanged()
    {
        this.values[0] = 0.0F;
        this.values[1] = 0.0F;
        this.values[2] = 0.0F;
        this.values[3] = 0.0F;
    }

    public void setValue(float f0, float f1, float f2, float f3)
    {
        if (this.getLocation() >= 0)
        {
            if (this.values[0] != f0 || this.values[1] != f1 || this.values[2] != f2 || this.values[3] != f3)
            {
                ARBShaderObjects.glUniform4fARB(this.getLocation(), f0, f1, f2, f3);
                Shaders.checkGLError(this.getName());
                this.values[0] = f0;
                this.values[1] = f1;
                this.values[2] = f2;
                this.values[3] = f3;
            }
        }
    }

    public float[] getValues()
    {
        return this.values;
    }
}
