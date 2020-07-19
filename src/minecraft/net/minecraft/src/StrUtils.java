package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StrUtils
{
    public static boolean equalsMask(String p_equalsMask_0_, String p_equalsMask_1_, char p_equalsMask_2_, char p_equalsMask_3_)
    {
        if (p_equalsMask_1_ != null && p_equalsMask_0_ != null)
        {
            if (p_equalsMask_1_.indexOf(p_equalsMask_2_) < 0)
            {
                return p_equalsMask_1_.indexOf(p_equalsMask_3_) < 0 ? p_equalsMask_1_.equals(p_equalsMask_0_) : equalsMaskSingle(p_equalsMask_0_, p_equalsMask_1_, p_equalsMask_3_);
            }
            else
            {
                List list = new ArrayList();
                String s = "" + p_equalsMask_2_;

                if (p_equalsMask_1_.startsWith(s))
                {
                    list.add("");
                }

                StringTokenizer stringtokenizer = new StringTokenizer(p_equalsMask_1_, s);

                while (stringtokenizer.hasMoreElements())
                {
                    list.add(stringtokenizer.nextToken());
                }

                if (p_equalsMask_1_.endsWith(s))
                {
                    list.add("");
                }

                String s1 = (String)list.get(0);

                if (!startsWithMaskSingle(p_equalsMask_0_, s1, p_equalsMask_3_))
                {
                    return false;
                }
                else
                {
                    String s2 = (String)list.get(list.size() - 1);

                    if (!endsWithMaskSingle(p_equalsMask_0_, s2, p_equalsMask_3_))
                    {
                        return false;
                    }
                    else
                    {
                        int i = 0;

                        for (int j = 0; j < list.size(); ++j)
                        {
                            String s3 = (String)list.get(j);

                            if (s3.length() > 0)
                            {
                                int k = indexOfMaskSingle(p_equalsMask_0_, s3, i, p_equalsMask_3_);

                                if (k < 0)
                                {
                                    return false;
                                }

                                i = k + s3.length();
                            }
                        }

                        return true;
                    }
                }
            }
        }
        else
        {
            return p_equalsMask_1_ == p_equalsMask_0_;
        }
    }

    private static boolean equalsMaskSingle(String p_equalsMaskSingle_0_, String p_equalsMaskSingle_1_, char p_equalsMaskSingle_2_)
    {
        if (p_equalsMaskSingle_0_ != null && p_equalsMaskSingle_1_ != null)
        {
            if (p_equalsMaskSingle_0_.length() != p_equalsMaskSingle_1_.length())
            {
                return false;
            }
            else
            {
                for (int i = 0; i < p_equalsMaskSingle_1_.length(); ++i)
                {
                    char c0 = p_equalsMaskSingle_1_.charAt(i);

                    if (c0 != p_equalsMaskSingle_2_ && p_equalsMaskSingle_0_.charAt(i) != c0)
                    {
                        return false;
                    }
                }

                return true;
            }
        }
        else
        {
            return p_equalsMaskSingle_0_ == p_equalsMaskSingle_1_;
        }
    }

    private static int indexOfMaskSingle(String p_indexOfMaskSingle_0_, String p_indexOfMaskSingle_1_, int p_indexOfMaskSingle_2_, char p_indexOfMaskSingle_3_)
    {
        if (p_indexOfMaskSingle_0_ != null && p_indexOfMaskSingle_1_ != null)
        {
            if (p_indexOfMaskSingle_2_ >= 0 && p_indexOfMaskSingle_2_ <= p_indexOfMaskSingle_0_.length())
            {
                if (p_indexOfMaskSingle_0_.length() < p_indexOfMaskSingle_2_ + p_indexOfMaskSingle_1_.length())
                {
                    return -1;
                }
                else
                {
                    for (int i = p_indexOfMaskSingle_2_; i + p_indexOfMaskSingle_1_.length() <= p_indexOfMaskSingle_0_.length(); ++i)
                    {
                        String s = p_indexOfMaskSingle_0_.substring(i, i + p_indexOfMaskSingle_1_.length());

                        if (equalsMaskSingle(s, p_indexOfMaskSingle_1_, p_indexOfMaskSingle_3_))
                        {
                            return i;
                        }
                    }

                    return -1;
                }
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }

    private static boolean endsWithMaskSingle(String p_endsWithMaskSingle_0_, String p_endsWithMaskSingle_1_, char p_endsWithMaskSingle_2_)
    {
        if (p_endsWithMaskSingle_0_ != null && p_endsWithMaskSingle_1_ != null)
        {
            if (p_endsWithMaskSingle_0_.length() < p_endsWithMaskSingle_1_.length())
            {
                return false;
            }
            else
            {
                String s = p_endsWithMaskSingle_0_.substring(p_endsWithMaskSingle_0_.length() - p_endsWithMaskSingle_1_.length(), p_endsWithMaskSingle_0_.length());
                return equalsMaskSingle(s, p_endsWithMaskSingle_1_, p_endsWithMaskSingle_2_);
            }
        }
        else
        {
            return p_endsWithMaskSingle_0_ == p_endsWithMaskSingle_1_;
        }
    }

    private static boolean startsWithMaskSingle(String p_startsWithMaskSingle_0_, String p_startsWithMaskSingle_1_, char p_startsWithMaskSingle_2_)
    {
        if (p_startsWithMaskSingle_0_ != null && p_startsWithMaskSingle_1_ != null)
        {
            if (p_startsWithMaskSingle_0_.length() < p_startsWithMaskSingle_1_.length())
            {
                return false;
            }
            else
            {
                String s = p_startsWithMaskSingle_0_.substring(0, p_startsWithMaskSingle_1_.length());
                return equalsMaskSingle(s, p_startsWithMaskSingle_1_, p_startsWithMaskSingle_2_);
            }
        }
        else
        {
            return p_startsWithMaskSingle_0_ == p_startsWithMaskSingle_1_;
        }
    }

    public static boolean equalsMask(String p_equalsMask_0_, String[] p_equalsMask_1_, char p_equalsMask_2_)
    {
        for (int i = 0; i < p_equalsMask_1_.length; ++i)
        {
            String s = p_equalsMask_1_[i];

            if (equalsMask(p_equalsMask_0_, s, p_equalsMask_2_))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean equalsMask(String p_equalsMask_0_, String p_equalsMask_1_, char p_equalsMask_2_)
    {
        if (p_equalsMask_1_ != null && p_equalsMask_0_ != null)
        {
            if (p_equalsMask_1_.indexOf(p_equalsMask_2_) < 0)
            {
                return p_equalsMask_1_.equals(p_equalsMask_0_);
            }
            else
            {
                List list = new ArrayList();
                String s = "" + p_equalsMask_2_;

                if (p_equalsMask_1_.startsWith(s))
                {
                    list.add("");
                }

                StringTokenizer stringtokenizer = new StringTokenizer(p_equalsMask_1_, s);

                while (stringtokenizer.hasMoreElements())
                {
                    list.add(stringtokenizer.nextToken());
                }

                if (p_equalsMask_1_.endsWith(s))
                {
                    list.add("");
                }

                String s1 = (String)list.get(0);

                if (!p_equalsMask_0_.startsWith(s1))
                {
                    return false;
                }
                else
                {
                    String s2 = (String)list.get(list.size() - 1);

                    if (!p_equalsMask_0_.endsWith(s2))
                    {
                        return false;
                    }
                    else
                    {
                        int i = 0;

                        for (int j = 0; j < list.size(); ++j)
                        {
                            String s3 = (String)list.get(j);

                            if (s3.length() > 0)
                            {
                                int k = p_equalsMask_0_.indexOf(s3, i);

                                if (k < 0)
                                {
                                    return false;
                                }

                                i = k + s3.length();
                            }
                        }

                        return true;
                    }
                }
            }
        }
        else
        {
            return p_equalsMask_1_ == p_equalsMask_0_;
        }
    }

    public static String[] split(String p_split_0_, String p_split_1_)
    {
        if (p_split_0_ != null && p_split_0_.length() > 0)
        {
            if (p_split_1_ == null)
            {
                return new String[] {p_split_0_};
            }
            else
            {
                List list = new ArrayList();
                int i = 0;

                for (int j = 0; j < p_split_0_.length(); ++j)
                {
                    char c0 = p_split_0_.charAt(j);

                    if (equals(c0, p_split_1_))
                    {
                        list.add(p_split_0_.substring(i, j));
                        i = j + 1;
                    }
                }

                list.add(p_split_0_.substring(i, p_split_0_.length()));
                return (String[])list.toArray(new String[list.size()]);
            }
        }
        else
        {
            return new String[0];
        }
    }

    private static boolean equals(char p_equals_0_, String p_equals_1_)
    {
        for (int i = 0; i < p_equals_1_.length(); ++i)
        {
            if (p_equals_1_.charAt(i) == p_equals_0_)
            {
                return true;
            }
        }

        return false;
    }

    public static boolean equalsTrim(String p_equalsTrim_0_, String p_equalsTrim_1_)
    {
        if (p_equalsTrim_0_ != null)
        {
            p_equalsTrim_0_ = p_equalsTrim_0_.trim();
        }

        if (p_equalsTrim_1_ != null)
        {
            p_equalsTrim_1_ = p_equalsTrim_1_.trim();
        }

        return equals(p_equalsTrim_0_, p_equalsTrim_1_);
    }

    public static boolean isEmpty(String p_isEmpty_0_)
    {
        if (p_isEmpty_0_ == null)
        {
            return true;
        }
        else
        {
            return p_isEmpty_0_.trim().length() <= 0;
        }
    }

    public static String stringInc(String p_stringInc_0_)
    {
        int i = parseInt(p_stringInc_0_, -1);

        if (i == -1)
        {
            return "";
        }
        else
        {
            ++i;
            String s = "" + i;
            return s.length() > p_stringInc_0_.length() ? "" : fillLeft("" + i, p_stringInc_0_.length(), '0');
        }
    }

    public static int parseInt(String p_parseInt_0_, int p_parseInt_1_)
    {
        if (p_parseInt_0_ == null)
        {
            return p_parseInt_1_;
        }
        else
        {
            try
            {
                return Integer.parseInt(p_parseInt_0_);
            }
            catch (NumberFormatException var3)
            {
                return p_parseInt_1_;
            }
        }
    }

    public static boolean isFilled(String p_isFilled_0_)
    {
        return !isEmpty(p_isFilled_0_);
    }

    public static String addIfNotContains(String p_addIfNotContains_0_, String p_addIfNotContains_1_)
    {
        for (int i = 0; i < p_addIfNotContains_1_.length(); ++i)
        {
            if (p_addIfNotContains_0_.indexOf(p_addIfNotContains_1_.charAt(i)) < 0)
            {
                p_addIfNotContains_0_ = p_addIfNotContains_0_ + p_addIfNotContains_1_.charAt(i);
            }
        }

        return p_addIfNotContains_0_;
    }

    public static String fillLeft(String p_fillLeft_0_, int p_fillLeft_1_, char p_fillLeft_2_)
    {
        if (p_fillLeft_0_ == null)
        {
            p_fillLeft_0_ = "";
        }

        if (p_fillLeft_0_.length() >= p_fillLeft_1_)
        {
            return p_fillLeft_0_;
        }
        else
        {
            StringBuffer stringbuffer = new StringBuffer();
            int i = p_fillLeft_1_ - p_fillLeft_0_.length();

            while (stringbuffer.length() < i)
            {
                stringbuffer.append(p_fillLeft_2_);
            }

            return stringbuffer.toString() + p_fillLeft_0_;
        }
    }

    public static String fillRight(String p_fillRight_0_, int p_fillRight_1_, char p_fillRight_2_)
    {
        if (p_fillRight_0_ == null)
        {
            p_fillRight_0_ = "";
        }

        if (p_fillRight_0_.length() >= p_fillRight_1_)
        {
            return p_fillRight_0_;
        }
        else
        {
            StringBuffer stringbuffer = new StringBuffer(p_fillRight_0_);

            while (stringbuffer.length() < p_fillRight_1_)
            {
                stringbuffer.append(p_fillRight_2_);
            }

            return stringbuffer.toString();
        }
    }

    public static boolean equals(Object p_equals_0_, Object p_equals_1_)
    {
        if (p_equals_0_ == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_0_ != null && p_equals_0_.equals(p_equals_1_))
        {
            return true;
        }
        else
        {
            return p_equals_1_ != null && p_equals_1_.equals(p_equals_0_);
        }
    }

    public static boolean startsWith(String p_startsWith_0_, String[] p_startsWith_1_)
    {
        if (p_startsWith_0_ == null)
        {
            return false;
        }
        else if (p_startsWith_1_ == null)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < p_startsWith_1_.length; ++i)
            {
                String s = p_startsWith_1_[i];

                if (p_startsWith_0_.startsWith(s))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean endsWith(String p_endsWith_0_, String[] p_endsWith_1_)
    {
        if (p_endsWith_0_ == null)
        {
            return false;
        }
        else if (p_endsWith_1_ == null)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < p_endsWith_1_.length; ++i)
            {
                String s = p_endsWith_1_[i];

                if (p_endsWith_0_.endsWith(s))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public static String removePrefix(String p_removePrefix_0_, String p_removePrefix_1_)
    {
        if (p_removePrefix_0_ != null && p_removePrefix_1_ != null)
        {
            if (p_removePrefix_0_.startsWith(p_removePrefix_1_))
            {
                p_removePrefix_0_ = p_removePrefix_0_.substring(p_removePrefix_1_.length());
            }

            return p_removePrefix_0_;
        }
        else
        {
            return p_removePrefix_0_;
        }
    }

    public static String removeSuffix(String p_removeSuffix_0_, String p_removeSuffix_1_)
    {
        if (p_removeSuffix_0_ != null && p_removeSuffix_1_ != null)
        {
            if (p_removeSuffix_0_.endsWith(p_removeSuffix_1_))
            {
                p_removeSuffix_0_ = p_removeSuffix_0_.substring(0, p_removeSuffix_0_.length() - p_removeSuffix_1_.length());
            }

            return p_removeSuffix_0_;
        }
        else
        {
            return p_removeSuffix_0_;
        }
    }

    public static String replaceSuffix(String p_replaceSuffix_0_, String p_replaceSuffix_1_, String p_replaceSuffix_2_)
    {
        if (p_replaceSuffix_0_ != null && p_replaceSuffix_1_ != null)
        {
            if (!p_replaceSuffix_0_.endsWith(p_replaceSuffix_1_))
            {
                return p_replaceSuffix_0_;
            }
            else
            {
                if (p_replaceSuffix_2_ == null)
                {
                    p_replaceSuffix_2_ = "";
                }

                p_replaceSuffix_0_ = p_replaceSuffix_0_.substring(0, p_replaceSuffix_0_.length() - p_replaceSuffix_1_.length());
                return p_replaceSuffix_0_ + p_replaceSuffix_2_;
            }
        }
        else
        {
            return p_replaceSuffix_0_;
        }
    }

    public static String replacePrefix(String p_replacePrefix_0_, String p_replacePrefix_1_, String p_replacePrefix_2_)
    {
        if (p_replacePrefix_0_ != null && p_replacePrefix_1_ != null)
        {
            if (!p_replacePrefix_0_.startsWith(p_replacePrefix_1_))
            {
                return p_replacePrefix_0_;
            }
            else
            {
                if (p_replacePrefix_2_ == null)
                {
                    p_replacePrefix_2_ = "";
                }

                p_replacePrefix_0_ = p_replacePrefix_0_.substring(p_replacePrefix_1_.length());
                return p_replacePrefix_2_ + p_replacePrefix_0_;
            }
        }
        else
        {
            return p_replacePrefix_0_;
        }
    }

    public static int findPrefix(String[] p_findPrefix_0_, String p_findPrefix_1_)
    {
        if (p_findPrefix_0_ != null && p_findPrefix_1_ != null)
        {
            for (int i = 0; i < p_findPrefix_0_.length; ++i)
            {
                String s = p_findPrefix_0_[i];

                if (s.startsWith(p_findPrefix_1_))
                {
                    return i;
                }
            }

            return -1;
        }
        else
        {
            return -1;
        }
    }

    public static int findSuffix(String[] p_findSuffix_0_, String p_findSuffix_1_)
    {
        if (p_findSuffix_0_ != null && p_findSuffix_1_ != null)
        {
            for (int i = 0; i < p_findSuffix_0_.length; ++i)
            {
                String s = p_findSuffix_0_[i];

                if (s.endsWith(p_findSuffix_1_))
                {
                    return i;
                }
            }

            return -1;
        }
        else
        {
            return -1;
        }
    }

    public static String[] remove(String[] p_remove_0_, int p_remove_1_, int p_remove_2_)
    {
        if (p_remove_0_ == null)
        {
            return p_remove_0_;
        }
        else if (p_remove_2_ > 0 && p_remove_1_ < p_remove_0_.length)
        {
            if (p_remove_1_ >= p_remove_2_)
            {
                return p_remove_0_;
            }
            else
            {
                List<String> list = new ArrayList<String>(p_remove_0_.length);

                for (int i = 0; i < p_remove_0_.length; ++i)
                {
                    String s = p_remove_0_[i];

                    if (i < p_remove_1_ || i >= p_remove_2_)
                    {
                        list.add(s);
                    }
                }

                String[] astring = (String[])list.toArray(new String[list.size()]);
                return astring;
            }
        }
        else
        {
            return p_remove_0_;
        }
    }

    public static String removeSuffix(String p_removeSuffix_0_, String[] p_removeSuffix_1_)
    {
        if (p_removeSuffix_0_ != null && p_removeSuffix_1_ != null)
        {
            int i = p_removeSuffix_0_.length();

            for (int j = 0; j < p_removeSuffix_1_.length; ++j)
            {
                String s = p_removeSuffix_1_[j];
                p_removeSuffix_0_ = removeSuffix(p_removeSuffix_0_, s);

                if (p_removeSuffix_0_.length() != i)
                {
                    break;
                }
            }

            return p_removeSuffix_0_;
        }
        else
        {
            return p_removeSuffix_0_;
        }
    }

    public static String removePrefix(String p_removePrefix_0_, String[] p_removePrefix_1_)
    {
        if (p_removePrefix_0_ != null && p_removePrefix_1_ != null)
        {
            int i = p_removePrefix_0_.length();

            for (int j = 0; j < p_removePrefix_1_.length; ++j)
            {
                String s = p_removePrefix_1_[j];
                p_removePrefix_0_ = removePrefix(p_removePrefix_0_, s);

                if (p_removePrefix_0_.length() != i)
                {
                    break;
                }
            }

            return p_removePrefix_0_;
        }
        else
        {
            return p_removePrefix_0_;
        }
    }

    public static String removePrefixSuffix(String p_removePrefixSuffix_0_, String[] p_removePrefixSuffix_1_, String[] p_removePrefixSuffix_2_)
    {
        p_removePrefixSuffix_0_ = removePrefix(p_removePrefixSuffix_0_, p_removePrefixSuffix_1_);
        p_removePrefixSuffix_0_ = removeSuffix(p_removePrefixSuffix_0_, p_removePrefixSuffix_2_);
        return p_removePrefixSuffix_0_;
    }

    public static String removePrefixSuffix(String p_removePrefixSuffix_0_, String p_removePrefixSuffix_1_, String p_removePrefixSuffix_2_)
    {
        return removePrefixSuffix(p_removePrefixSuffix_0_, new String[] {p_removePrefixSuffix_1_}, new String[] {p_removePrefixSuffix_2_});
    }

    public static String getSegment(String p_getSegment_0_, String p_getSegment_1_, String p_getSegment_2_)
    {
        if (p_getSegment_0_ != null && p_getSegment_1_ != null && p_getSegment_2_ != null)
        {
            int i = p_getSegment_0_.indexOf(p_getSegment_1_);

            if (i < 0)
            {
                return null;
            }
            else
            {
                int j = p_getSegment_0_.indexOf(p_getSegment_2_, i);
                return j < 0 ? null : p_getSegment_0_.substring(i, j + p_getSegment_2_.length());
            }
        }
        else
        {
            return null;
        }
    }

    public static String addSuffixCheck(String p_addSuffixCheck_0_, String p_addSuffixCheck_1_)
    {
        if (p_addSuffixCheck_0_ != null && p_addSuffixCheck_1_ != null)
        {
            return p_addSuffixCheck_0_.endsWith(p_addSuffixCheck_1_) ? p_addSuffixCheck_0_ : p_addSuffixCheck_0_ + p_addSuffixCheck_1_;
        }
        else
        {
            return p_addSuffixCheck_0_;
        }
    }

    public static String addPrefixCheck(String p_addPrefixCheck_0_, String p_addPrefixCheck_1_)
    {
        if (p_addPrefixCheck_0_ != null && p_addPrefixCheck_1_ != null)
        {
            return p_addPrefixCheck_0_.endsWith(p_addPrefixCheck_1_) ? p_addPrefixCheck_0_ : p_addPrefixCheck_1_ + p_addPrefixCheck_0_;
        }
        else
        {
            return p_addPrefixCheck_0_;
        }
    }
}
