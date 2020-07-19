package net.optifine.entity.model.anim;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TokenParser
{
    public static Token[] parse(String str) throws IOException, ParseException
    {
        Reader reader = new StringReader(str);
        PushbackReader pushbackreader = new PushbackReader(reader);
        List<Token> list = new ArrayList<Token>();

        while (true)
        {
            int i = pushbackreader.read();

            if (i < 0)
            {
                Token[] atoken = (Token[])list.toArray(new Token[list.size()]);
                return atoken;
            }

            char c0 = (char)i;

            if (!Character.isWhitespace(c0))
            {
                EnumTokenType enumtokentype = EnumTokenType.getTypeByFirstChar(c0);

                if (enumtokentype == null)
                {
                    throw new ParseException("Invalid character: '" + c0 + "', in: " + str);
                }

                Token token = readToken(c0, enumtokentype, pushbackreader);
                list.add(token);
            }
        }
    }

    private static Token readToken(char chFirst, EnumTokenType type, PushbackReader pr) throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(chFirst);

        while (type.getMaxLen() <= 0 || stringbuffer.length() < type.getMaxLen())
        {
            int i = pr.read();

            if (i < 0)
            {
                break;
            }

            char c0 = (char)i;

            if (!type.hasChar(c0))
            {
                pr.unread(c0);
                break;
            }

            stringbuffer.append(c0);
        }

        return new Token(type, stringbuffer.toString());
    }
}
