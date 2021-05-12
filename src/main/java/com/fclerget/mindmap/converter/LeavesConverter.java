package com.fclerget.mindmap.converter;

import com.fclerget.mindmap.model.Leaf;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LeavesConverter implements Converter<List<Leaf>, String> {

    private final static Pattern PATTERN = Pattern.compile("([a-zA-Z0-9]+\\/(?!\\/)*)");

    @Override
    public String convert(List<Leaf> leaves) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("root/\n");

        presentLeaves(stringBuilder, leaves, "\t");

        return stringBuilder.toString();
    }

    private void presentLeaves(StringBuilder stringBuilder, List<Leaf> leaves, String currentTab) {

        Map<String, List<Leaf>> commonLeaves = new HashMap<>();

        for (Leaf leaf : leaves) {

            String firstPathGroup = getFirstPathGroup(leaf.getPath());

            if (!commonLeaves.containsKey(firstPathGroup)) {
                commonLeaves.put(firstPathGroup, Lists.newArrayList());
            }

            leaf.setPath(leaf.getPath().replace(firstPathGroup, ""));

            commonLeaves.get(firstPathGroup).add(leaf);
        }

        for (Entry<String, List<Leaf>> entry : commonLeaves.entrySet()) {

            if (StringUtils.isBlank(entry.getKey())) {
                entry.getValue()
                        .stream()
                        .map(Leaf::getPath)
                        .map(path -> currentTab + path + "\n")
                        .forEach(stringBuilder::append);
            } else {
                stringBuilder.append(currentTab);
                stringBuilder.append(entry.getKey());
                stringBuilder.append("\n");
            }

            if (entry.getKey().contains("/")) {
                presentLeaves(stringBuilder, entry.getValue(), currentTab + "\t");
            }
        }
    }

    private String getFirstPathGroup(String path) {

        String extractedPath = "";

        Matcher matcher = PATTERN.matcher(path);

        if (matcher.find()) {
            extractedPath = matcher.group();
        }

        return extractedPath;
    }
}
