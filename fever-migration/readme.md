# Install
```bash
brew install python3 graphviz
python3 XXX.py
pip3 XXX
```
## Atom
```bash
brew install graphviz
brew cask install atom
apm install plantuml-viewer  
```
## IntelliJ IDEA & Chrome
```bash
brew install graphviz
plugin
```
# Create UML Diagrams
```puml
UML legend:
table = class
#pkey
+index
@startuml
    class user <<(T,olive)>> {
        user table info
        ==
        #id : bigint(11) -- ID
        +company_id : bigint(11) -- 公司ID
        name : varchar(64) -- 名称
    }
    user "1" -- "1..*" ticket
    class ticket <<(T,olive)>> {
        ticket table info
        ==
        #id : bigint(11) -- ID
        +company_id : bigint(11) -- 公司ID
        name : varchar(64) -- 名称
    }
@enduml
```
# Convert To SQL
```puml
./plantuml2mysql.py ../database.puml sampledb
```
# Reference
[plantuml2mysql](https://github.com/grafov/plantuml2mysql.git)