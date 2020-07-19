package shadersmod.client;

public class MultiTexID
{
    public int base;
    public int norm;
    public int spec;

    public MultiTexID(int baseTex, int normTex, int specTex)
    {
        this.base = baseTex;
        this.norm = normTex;
        this.spec = specTex;
    }
}
