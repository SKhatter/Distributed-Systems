import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
 
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
 
public class BigramPair implements WritableComparable {
 
    private Text first;
    private Text second;
    private IntWritable frequency;
 
   
    public BigramPair(String first, String second, int f) {
       this.first = new Text(first);
       this.second = new Text(second);
       this.frequency = new IntWritable(f);
    }
 
    public Text getFirst() {
        return first;
    }
 
    public Text getSecond() {
        return second;
    }
 
    public IntWritable getFrequency(){
    	return frequency;
    }
    
  
    @Override
    public void readFields(DataInput in) throws IOException {
        first.readFields(in);
        second.readFields(in);
        frequency.readFields(in);
    }
 
    @Override
    public void write(DataOutput out) throws IOException {
        first.write(out);
        second.write(out);
        frequency.write(out);
    }
 
    @Override
    public String toString() {
        return first + " " + second + " "+ frequency;
    }
 
    @Override
    public int hashCode(){
        return first.hashCode()*163 + second.hashCode() + frequency.hashCode();
    }
 
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof BigramPair)
        {
        	BigramPair tp = (BigramPair) o;
            return first.equals(tp.first) && second.equals(tp.second);
        }
        return false;
    }

	@Override
	public int compareTo(Object o) {
		
		BigramPair tp = (BigramPair) o;
		
		int c = tp.frequency.compareTo(frequency);
		
		if(c != 0)
			return c;
		
		int cmp = first.compareTo(tp.first);
		 
        if (cmp != 0) {
            return cmp;
        }
 
        return second.compareTo(tp.second);
	}
 
}
