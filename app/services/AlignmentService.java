package services;

import pt.fcup.bioinformatics.sequencealignment.costmatrix.BlosumCostMatrix;
import pt.fcup.bioinformatics.sequencealignment.costmatrix.MatrixCostType;
import pt.fcup.bioinformatics.sequencealignment.costmatrix.PamCostMatrix; 

import pt.fcup.bioinformatics.sequencealignment.AlignmentResult;
import pt.fcup.bioinformatics.sequencealignment.LocalAlignment;
import pt.fcup.bioinformatics.sequencealignment.GlobalAlignment;
import pt.fcup.bioinformatics.sequencealignment.AlignmentType;
import pt.fcup.bioinformatics.sequencealignment.AbstractAlignment;


import controllers.*;

public class AlignmentService{
	
	 
    public AlignmentResult apply(Application.AlignmentData data) {

        String sequenceA = data.sequenceA;
        String sequenceB = data.sequenceB;

        String matrixCost = data.matrixCost.toUpperCase();
        String alignmentType = data.alignmentType.toUpperCase();

        AlignmentType aType = AlignmentType.valueOf(alignmentType);
        MatrixCostType matrixCostType = MatrixCostType.valueOf(matrixCost);

        AbstractAlignment alignmentMethod;
        AlignmentResult result;

        if(aType.equals(AlignmentType.LOCAL)){
            alignmentMethod = new LocalAlignment();
            result = runAlignment(sequenceA, sequenceB, matrixCostType, alignmentMethod);

        }else {
            alignmentMethod = new GlobalAlignment();
            result = runAlignment(sequenceA, sequenceB, matrixCostType, alignmentMethod);
        }

        // logger.info(result.toString());

        System.out.println(result);

        return result;
    }

    private AlignmentResult runAlignment(String sequenceA, String sequenceB, MatrixCostType matrixCostType, AbstractAlignment alignmentMethod) {
        switch (matrixCostType) {
            case BLOSUM: {
                return alignmentMethod.align(new BlosumCostMatrix(), sequenceA, sequenceB);
            }

            case PAM: {
                return alignmentMethod.align(new PamCostMatrix(), sequenceA, sequenceB);
            }

            default: {
                return alignmentMethod.align(sequenceA, sequenceB);
            }
        }
    }

}