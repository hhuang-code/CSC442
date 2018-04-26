package bn.util;

public class Pair<L, R>
{
    private final L m_left;
    private final R m_right;
 
    public Pair(L left, R right)
    {
        this.m_left = left;
        this.m_right = right;
    }
 
    public L getLeft()
    {
        return m_left;
    }
 
    public R getRight()
    {
        return m_right;
    }
 
    @Override
    public int hashCode()
    {
        return m_left.hashCode() ^ m_right.hashCode();
    }
 
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (!(o instanceof Pair))
        {
            return false;
        }
        Pair<?, ?> pairo = (Pair<?, ?>) o;
        return this.m_left.equals(pairo.getLeft()) &&
                this.m_right.equals(pairo.getRight());
    }
 
    @Override
    public String toString()
    {
        return "Pair{" +
                "m_left=" + m_left +
                ", m_right=" + m_right +
                '}';
    }
}