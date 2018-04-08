import java.util.ArrayList;

public class MyGroup
{
    private String name, typeOfGroup;
    private TypeCollection typeCollection;

    ArrayList<Person> presons;

    MyGroup(String newName, TypeCollection typeOfList)
    {
        name = newName;
        typeCollection = typeOfList;
        switch(typeCollection)
        {
            case COLLECTION_TREESET:
                typeOfGroup = TypeCollection.COLLECTION_TREESET.toString();
                break;
            case COLLECTION_HASHSET:
                typeOfGroup = TypeCollection.COLLECTION_HASHSET.toString();
                break;
            case LIST_LINKEDLIST:
                typeOfGroup = TypeCollection.LIST_LINKEDLIST.toString();
                break;
            case LIST_ARRAYLIST:
                typeOfGroup = TypeCollection.LIST_ARRAYLIST.toString();
                break;
            case LIST_VECTOR:
                typeOfGroup = TypeCollection.LIST_VECTOR.toString();
                break;
            default:
                typeOfGroup = TypeCollection.LIST_VECTOR.toString();
                typeCollection = TypeCollection.LIST_VECTOR;
                break;
        }
    }

    public int getSize()
    {
        return 0;
    }

    public String getTypeOfGroup()
    {
        return typeOfGroup;
    }

    public String getName()
    {
        return name;
    }

}
