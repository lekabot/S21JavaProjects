package edu.school21.annotationProcessor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes({"edu.school21.annotationProcessor.HtmlInput",
        "edu.school21.annotationProcessor.HtmlForm"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        System.out.println("INIT");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            System.out.println("PROCESSING " + e.getSimpleName());

            String filename = "target/generated-sources/annotations/" +
                    e.getAnnotation(HtmlForm.class).fileName();
            File generatingFile = new File(filename);

            try (FileWriter writer = new FileWriter(generatingFile, false)) {
                String action = e.getAnnotation(HtmlForm.class).action();
                String method = e.getAnnotation(HtmlForm.class).method();
                writer.write("<form action = \"" + action + "\" method = \"" + method + "\">\n");

                for (Element subE : e.getEnclosedElements()) {
                    if (subE.getKind() == ElementKind.FIELD) {
                        String type = subE.getAnnotation(HtmlInput.class).type();
                        String name = subE.getAnnotation(HtmlInput.class).name();
                        String placeholder = subE.getAnnotation(HtmlInput.class).placeholder();

                        writer.write("\t<input type = \"" + type + "\" " +
                                "name = \"" + name + "\" " +
                                "placeholder = \"" + placeholder + "\">\n");
                    }
                }

                writer.write("\t<input type = \"submit\" value = \"Send\">\n</form>");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return true;
    }
}