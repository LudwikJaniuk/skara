        var extendedHeaders = List.of(
            "old mode ",
            "new mode ",
            "deleted file mode ",
            "new file mode ",
            "copy from ",
            "copy to ",
            "rename from ",
            "rename to ",
            "similarity index ",
            "dissimilarity index ",
            "index "
        );
        while (i < lines.size()) {
            var line = lines.get(i);
            if (extendedHeaders.stream().noneMatch(h -> line.startsWith(h))) {
                break;
            }
            i++;
        }
            if (lines.get(i).startsWith("Binary files ") && lines.get(i).endsWith(" differ")) {
                i++;
                continue;
            }
            var hunkLines = lines.subList(i + 1, nextHeader);
            if (!hunkLines.isEmpty()) {
                hunks.addAll(parseSingleFileDiff(sourceRange, targetRange, hunkLines));
            }
        var targetHasNewlineAtEndOfFile = true;
        var sourceHasNewlineAtEndOfFile = true;
        var previousLineType = "";
                previousLineType = "-";
                previousLineType = "+";
            } else if (line.startsWith(" ")) {
                previousLineType = " ";
                hunks.add(new Hunk(new Range(sourceStart, sourceLines.size()), sourceLines, sourceHasNewlineAtEndOfFile,
                                   new Range(targetStart, targetLines.size()), targetLines, targetHasNewlineAtEndOfFile));
                sourceLines = new ArrayList<>();
                targetLines = new ArrayList<>();

                targetHasNewlineAtEndOfFile = true;
                sourceHasNewlineAtEndOfFile = true;
            } else if (line.equals("\\ No newline at end of file")) {
                if (previousLineType.equals("+")) {
                    targetHasNewlineAtEndOfFile = false;
                } else if (previousLineType.equals("-")) {
                    sourceHasNewlineAtEndOfFile = false;
                }
                i++;
            } else if (line.startsWith("Binary files") && line.endsWith("differ")) {
                i++;
            } else {
                throw new IllegalStateException("Unexpected diff line: " + line);
            hunks.add(new Hunk(new Range(sourceStart, sourceLines.size()), sourceLines, sourceHasNewlineAtEndOfFile,
                               new Range(targetStart, targetLines.size()), targetLines, targetHasNewlineAtEndOfFile));