// Copyright (C) 2016  Nathan Lowe
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
package soundclip.input;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class KeyMap
{
    // Transport
    private final List<KeyCombination> goKeys = new LinkedList<>();
    private final List<KeyCombination> togglePauseKeys = new LinkedList<>();
    private final List<KeyCombination> panicKeys = new LinkedList<>();
    private final List<KeyCombination> fadeOutKeys = new LinkedList<>();
    
    // Focus
    private final List<KeyCombination> focusNextCue = new LinkedList<>();
    private final List<KeyCombination> focusPreviousCue = new LinkedList<>();
    private final List<KeyCombination> focusNextList = new LinkedList<>();
    private final List<KeyCombination> focusPreviousList = new LinkedList<>();

    // Utility
    private final List<KeyCombination> saveProject = new LinkedList<>();
    private final List<KeyCombination> lockWorkspace = new LinkedList<>();
    private final List<KeyCombination> unlockWorkspace = new LinkedList<>();
    
    public KeyMap(JsonNode node)
    {
        Collections.addAll(goKeys,
                new KeyCombination(KeyCode.SPACE),
                new KeyCombination(KeyCode.NUMPAD0)
        );
        togglePauseKeys.add(new KeyCombination(KeyCode.TAB));
        panicKeys.add(new KeyCombination(KeyCode.ESCAPE));
        fadeOutKeys.add(new KeyCombination(KeyCode.BACK_SPACE));

        Collections.addAll(focusNextCue,
                new KeyCombination(KeyCode.DOWN),
                new KeyCombination(KeyCode.RIGHT),
                new KeyCombination(KeyCode.NUMPAD2),
                new KeyCombination(KeyCode.NUMPAD6)
        );
        Collections.addAll(focusPreviousCue,
                new KeyCombination(KeyCode.UP),
                new KeyCombination(KeyCode.LEFT),
                new KeyCombination(KeyCode.NUMPAD8),
                new KeyCombination(KeyCode.NUMPAD4)
        );

        Collections.addAll(focusNextList,
                new KeyCombination(KeyCode.PAGE_UP)
        );

        Collections.addAll(focusPreviousList,
                new KeyCombination(KeyCode.PAGE_DOWN)
        );

        saveProject.add(new KeyCombination(KeyCode.S, true, false, false, false));
        lockWorkspace.add(new KeyCombination(KeyCode.L, true, false, false, false));
        unlockWorkspace.add(new KeyCombination(KeyCode.L, true, false, true, false));

        if(node != null)
        {
            loadMap(node, goKeys, "go");
            loadMap(node, togglePauseKeys, "pause");
            loadMap(node, panicKeys, "panic");
            loadMap(node, fadeOutKeys, "fadeOut");

            loadMap(node, focusNextCue, "nextCue");
            loadMap(node, focusPreviousCue, "previousCue");
            loadMap(node, focusNextList, "nextList");
            loadMap(node, focusPreviousList, "previousList");

            loadMap(node, saveProject, "saveProject");
            loadMap(node, lockWorkspace, "lockWorkspace");
            loadMap(node, unlockWorkspace, "unlockWorkspace");
        }
    }

    public void save(JsonGenerator writer) throws IOException
    {
        writer.writeObjectFieldStart("keyMap");
        {
            saveMap(writer, goKeys, "go");
            saveMap(writer, togglePauseKeys, "pause");
            saveMap(writer, panicKeys, "panic");
            saveMap(writer, fadeOutKeys, "fadeOut");

            saveMap(writer, focusNextCue, "nextCue");
            saveMap(writer, focusPreviousCue, "previousCue");
            saveMap(writer, focusNextList, "nextList");
            saveMap(writer, focusPreviousList, "previousList");

            saveMap(writer, saveProject, "saveProject");
            saveMap(writer, lockWorkspace, "lockWorkspace");
            saveMap(writer, unlockWorkspace, "unlockWorkspace");
        }
        writer.writeEndObject();
    }

    private void saveMap(JsonGenerator writer, List<KeyCombination> map, String name) throws IOException
    {
        writer.writeArrayFieldStart(name);
        for(KeyCombination kc : map)
        {
            writer.writeString(kc.toString());
        }
        writer.writeEndArray();
    }

    private void loadMap(JsonNode collection, List<KeyCombination> map, String name)
    {
        if(!collection.has(name)) return;
        System.out.println("Loading keymap for " + name);

        for(JsonNode n : collection.get(name))
        {
            map.add(new KeyCombination(n.asText()));
        }
    }

    public List<KeyCombination> getGoKeys()
    {
        return goKeys;
    }

    public List<KeyCombination> getPanicKeys()
    {
        return panicKeys;
    }

    public List<KeyCombination> getFadeOutKeys()
    {
        return fadeOutKeys;
    }

    public List<KeyCombination> getFocusNextCue()
    {
        return focusNextCue;
    }

    public List<KeyCombination> getFocusPreviousCue()
    {
        return focusPreviousCue;
    }

    public List<KeyCombination> getFocusNextList()
    {
        return focusNextList;
    }

    public List<KeyCombination> getFocusPreviousList()
    {
        return focusPreviousList;
    }

    public List<KeyCombination> getSaveProject()
    {
        return saveProject;
    }

    public List<KeyCombination> getLockWorkspace()
    {
        return lockWorkspace;
    }

    public List<KeyCombination> getUnlockWorkspace()
    {
        return unlockWorkspace;
    }

    public List<KeyCombination> getTogglePauseKeys() {
        return togglePauseKeys;
    }
}
