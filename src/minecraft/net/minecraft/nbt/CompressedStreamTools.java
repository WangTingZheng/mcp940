package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

public class CompressedStreamTools
{
    /**
     * Load the gzipped compound from the inputstream.
     */
    public static NBTTagCompound readCompressed(InputStream is) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));
        NBTTagCompound nbttagcompound;

        try
        {
            nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
        }
        finally
        {
            datainputstream.close();
        }

        return nbttagcompound;
    }

    /**
     * Write the compound, gzipped, to the outputstream.
     */
    public static void writeCompressed(NBTTagCompound compound, OutputStream outputStream) throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));

        try
        {
            write(compound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    public static void safeWrite(NBTTagCompound compound, File fileIn) throws IOException
    {
        File file1 = new File(fileIn.getAbsolutePath() + "_tmp");

        if (file1.exists())
        {
            file1.delete();
        }

        write(compound, file1);

        if (fileIn.exists())
        {
            fileIn.delete();
        }

        if (fileIn.exists())
        {
            throw new IOException("Failed to delete " + fileIn);
        }
        else
        {
            file1.renameTo(fileIn);
        }
    }

    public static void write(NBTTagCompound compound, File fileIn) throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(fileIn));

        try
        {
            write(compound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    @Nullable
    public static NBTTagCompound read(File fileIn) throws IOException
    {
        if (!fileIn.exists())
        {
            return null;
        }
        else
        {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(fileIn));
            NBTTagCompound nbttagcompound;

            try
            {
                nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
            }
            finally
            {
                datainputstream.close();
            }

            return nbttagcompound;
        }
    }

    /**
     * Reads from a CompressedStream.
     */
    public static NBTTagCompound read(DataInputStream inputStream) throws IOException
    {
        return read(inputStream, NBTSizeTracker.INFINITE);
    }

    /**
     * Reads the given DataInput, constructs, and returns an NBTTagCompound with the data from the DataInput
     */
    public static NBTTagCompound read(DataInput input, NBTSizeTracker accounter) throws IOException
    {
        NBTBase nbtbase = read(input, 0, accounter);

        if (nbtbase instanceof NBTTagCompound)
        {
            return (NBTTagCompound)nbtbase;
        }
        else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void write(NBTTagCompound compound, DataOutput output) throws IOException
    {
        writeTag(compound, output);
    }

    private static void writeTag(NBTBase tag, DataOutput output) throws IOException
    {
        output.writeByte(tag.getId());

        if (tag.getId() != 0)
        {
            output.writeUTF("");
            tag.write(output);
        }
    }

    private static NBTBase read(DataInput input, int depth, NBTSizeTracker accounter) throws IOException
    {
        byte b0 = input.readByte();

        if (b0 == 0)
        {
            return new NBTTagEnd();
        }
        else
        {
            input.readUTF();
            NBTBase nbtbase = NBTBase.createNewByType(b0);

            try
            {
                nbtbase.read(input, depth, accounter);
                return nbtbase;
            }
            catch (IOException ioexception)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
                crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
                throw new ReportedException(crashreport);
            }
        }
    }
}
