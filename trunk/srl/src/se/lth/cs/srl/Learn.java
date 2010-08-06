package se.lth.cs.srl;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import se.lth.cs.srl.io.AllCoNLL09Reader;
import se.lth.cs.srl.io.SentenceReader;
import se.lth.cs.srl.options.LearnOptions;
import se.lth.cs.srl.pipeline.Pipeline;
import se.lth.cs.srl.pipeline.Reranker;

public class Learn {

	public static LearnOptions learnOptions;
	
	
	public static void main(String[] args) throws IOException{
		learnOptions=new LearnOptions(args);
		learn();
	}
	
	private static void learn() throws IOException {
		ZipOutputStream zos=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(learnOptions.modelFile)));
		if(learnOptions.trainReranker){
			new Reranker(learnOptions,zos);
		} else {
			SentenceReader reader=new AllCoNLL09Reader(learnOptions.inputCorpus);
			Pipeline.trainNewPipeline(reader, learnOptions.getFeatureFiles(), zos);
		}
		zos.close();
	}
}