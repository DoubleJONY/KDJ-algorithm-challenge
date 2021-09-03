import java.util.*;


class Solution {
    public int solution(int[] priorities, int location) {
        FastPrinter printer = new FastPrinter();

        for (int priority : priorities)
            printer.addDocument(priority);

        return printer.printFor(printer.getDocument(location));
    }
}


class FastPrinter {
    public static final int MIN_PRIORITY = 1;
    public static final int MAX_PRIORITY = 9;

    private final List<Document> documents;
    private final Map<Integer, List<Document>> docsByPriority;
    private int cursor;

    public FastPrinter() {
        docsByPriority = initializePriorityDocumentPool();
        documents = new ArrayList<>();
        cursor = 0;
    }

    private Map<Integer, List<Document>> initializePriorityDocumentPool() {
        Map<Integer, List<Document>> pool = new HashMap<>();
        for (int p = MIN_PRIORITY; p <= MAX_PRIORITY; p++)
            pool.put(p, new ArrayList<>());
        return pool;
    }

    public int printFor(Document end) {
        int nPrintedDocs = 0;

        for (int p = MAX_PRIORITY; p > end.priority; p--)
            nPrintedDocs += printAllDocumentsInPriority(p);

        nPrintedDocs += printForInPriority(end.priority, end);

        return nPrintedDocs;
    }

    private int printAllDocumentsInPriority(int priority) {
        List<Document> pool = docsByPriority.get(priority);
        int nPrintedDocs = pool.size();

        if (pool.isEmpty())
            return nPrintedDocs;

        Document firstDoc = findNextDocumentInPriority(priority);
        Document lastDoc = getPrevDocumentInPriority(priority, firstDoc);

        cursor = lastDoc.index;

        return nPrintedDocs;
    }

    private int printForInPriority(int priority, Document lastDoc) {
        List<Document> pool = docsByPriority.get(priority);
        Document firstDoc = findNextDocumentInPriority(priority);

        int nPrintedDocs;
        if (firstDoc.poolIndex <= lastDoc.poolIndex)
            nPrintedDocs = lastDoc.poolIndex - firstDoc.poolIndex + 1;
        else
            nPrintedDocs = (pool.size() + lastDoc.poolIndex) - firstDoc.poolIndex + 1;

        cursor = lastDoc.index;

        return nPrintedDocs;
    }

    private Document findNextDocumentInPriority(int priority) {
        List<Document> pool = docsByPriority.get(priority);
        int nextPoolIndex = binarySearchGreaterThanOrEqual(pool, 0, pool.size(), cursor);
        if (nextPoolIndex >= pool.size())
            nextPoolIndex = 0;
        return pool.get(nextPoolIndex);
    }

    private int binarySearchGreaterThanOrEqual(List<Document> pool, int low, int high, int keyIndex) {
        while (low < high) {
            int mid = (low + high) / 2;
            if (pool.get(mid).index < keyIndex)
                low = mid + 1;
            else
                high = mid;
        }
        return high;
    }

    private Document getPrevDocumentInPriority(int priority, Document firstDoc) {
        List<Document> pool = docsByPriority.get(priority);
        int prevIndex = firstDoc.poolIndex - 1;
        if (prevIndex < 0)
            prevIndex += pool.size();
        return pool.get(prevIndex);
    }

    public void addDocument(int priority) {
        List<Document> pool = docsByPriority.get(priority);

        Document doc = new Document(priority, documents.size(), pool.size());
        documents.add(doc);
        pool.add(doc);
    }

    public Document getDocument(int location) {
        return documents.get(location);
    }
}


class Document {
    public int priority;
    public int index;
    public int poolIndex;

    public Document(int priority, int index, int poolIndex) {
        this.priority = priority;
        this.index = index;
        this.poolIndex = poolIndex;
    }
}
