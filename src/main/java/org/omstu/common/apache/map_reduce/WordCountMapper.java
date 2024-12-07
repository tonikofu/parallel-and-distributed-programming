package org.omstu.common.apache.map_reduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final Text word = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer tokenizer = new StringTokenizer(value.toString().toLowerCase());

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().replaceAll("\\W+", "");

            if (!token.isEmpty()) {
                word.set(token);
                context.write(word, one);
            }
        }
    }
}
