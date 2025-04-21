class LLNode<T extends Comparable<T>> {
    T data;
    LLNode<T> next;

    public LLNode(T data) {
        this.data = data;
        this.next = null;
    }
}
