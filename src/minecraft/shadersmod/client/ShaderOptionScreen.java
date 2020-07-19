package shadersmod.client;

public class ShaderOptionScreen extends ShaderOption
{
    public ShaderOptionScreen(String name)
    {
        super(name, (String)null, (String)null, new String[] {null}, (String)null, (String)null);
    }

    public String getNameText()
    {
        return Shaders.translate("screen." + this.getName(), this.getName());
    }

    public String getDescriptionText()
    {
        return Shaders.translate("screen." + this.getName() + ".comment", (String)null);
    }
}
