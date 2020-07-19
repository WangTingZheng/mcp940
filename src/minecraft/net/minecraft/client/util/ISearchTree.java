package net.minecraft.client.util;

import java.util.List;

public interface ISearchTree<T>
{
    List<T> search(String searchText);
}
