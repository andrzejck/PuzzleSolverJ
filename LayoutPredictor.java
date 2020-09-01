package mensa;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.datavec.image.loader.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class LayoutPredictor {
    String simpleMlp;
    MultiLayerNetwork model;
    public LayoutPredictor()  {
        try {
            String simpleMlp = "/home/ar/dev/puzzleSolver/dataSets/modelLeNet5.h5";

            model = KerasModelImport.
                    importKerasSequentialModelAndWeights(simpleMlp);
            System.out.println("model imported");
        } catch (IOException e) {

            e.printStackTrace();
        } catch (InvalidKerasConfigurationException e) {

            e.printStackTrace();
        } catch (UnsupportedKerasConfigurationException e) {

            e.printStackTrace();
        }
    }

    public int predict(BufferedImage image){
        ImageLoader imageLoader = new ImageLoader(50,50,1);


        INDArray indArray = imageLoader.asMatrix(image);
        //DataNormalization scaler = new ImagePreProcessingScaler(0, 1, 22);
        float a  = indArray.getFloat(0,0);
        float b = indArray.getFloat(40,40);

        //scaler.transform(indArray);
        int c = indArray.getInt(0,0);
        int d = indArray.getInt(40,40);

        //INDArray indArray = imageLoader.asMatrix(image);
        //return model.output(indArray).getInt(0);
        //NativeImageLoader loader = new NativeImageLoader(50, 50, 1);
        int res = -1;
        res = model.predict(indArray.reshape(1,1,50,50).add(16777216).div(16777215))[0];

        return res;

    }
}
