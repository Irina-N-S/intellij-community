package com.siyeh.ipp.switchtoif;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiJavaToken;
import com.siyeh.ipp.base.PsiElementPredicate;

class IfToSwitchPredicate implements PsiElementPredicate{
    public boolean satisfiedBy(PsiElement element){
        if(!(element instanceof PsiJavaToken)){
            return false;
        }
        final String text = element.getText();
        if(!"if".equals(text)){
            return false;
        }
        if(!(element.getParent() instanceof PsiIfStatement)){
            return false;
        }
        final PsiIfStatement statement = (PsiIfStatement) element.getParent();
        return CaseUtil.getCaseExpression(statement) != null;
    }
}
