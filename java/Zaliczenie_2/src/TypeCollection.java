enum TypeCollection
{
    LIST_VECTOR("LISTA", "(klasa Vector)"),
    LIST_ARRAYLIST("LISTA", "(klasa AraryList)"),
    LIST_LINKEDLIST("LISTA", "(klasa LinkedList)"),
    COLLECTION_HASHSET("ZBIÓR", "(klasa HashSet)"),
    COLLECTION_TREESET("ZBIÓR", "(TreeSet)");

    private String type, descripson;

    private TypeCollection(String type, String descripson)
    {
        this.type = type;
        this.descripson = descripson;
    }

    @Override
    public String toString()
    {
        return type+ " "+descripson;
    }


}