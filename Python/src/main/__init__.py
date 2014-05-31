text = 'Hello this is an essay "this is a quote"(100).'

class Highlight:
    color = (1.0,1.0,1.0)
    start = 0
    end = 0
    message = ''
    
    def __init__(self, color, start, end, message = ''):
        self.color = color
        self.start = start
        self.end = end
        self.message = message
        
    def __str__(self):
        return '' + self.color.__str__()+' ' + self.start.__str__() + ' ' + self.end.__str__() + ' ' + self.message
        
def highlightQuote(str, index = 0):
    """TODO: Handle nested quotes"""
    start = index
    index += 1
  
    while(str[index] != '"'):
        index += 1
    
    endQuote = index
    gap = 0
    
    while(str[index] != '('):
        if index >= len(str):
            return Highlight((1, 0, 0),start, endQuote, 'No citation found')
        index += 1
        gap += 1
    startParen = index
    while(str[index] != ')'):
        if index >= len(str):
            return Highlight((1, 0, 0),startParen, len(str), 'Missing )')
        index += 1
    if gap < 20:
        return Highlight((0,1,0), start, index)
    else:
        return Highlight((1,1,0), start, index)
        
   
    

        



def highlight(str):
    highlights = []
    parensDepth = 0
    index = 0
    while index < len(str):
        if str[index] == '"':
            highlight = highlightQuote(str, index)
            highlights.append(highlight)
            index = highlight.end
        else:
            index += 1
            
            
        
        
    
    
    return highlights
    

h = highlight(text)
print(h[0])
    
    
    
    