text = "HELLOOOOOOOO"
lastBlockQuote = ''

class Highlight:
    color = "#FFFFFF"
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
    quote = ""
    assert index < len(str)
    index += 1
    assert index < len(str)
    color = "#00FF00"
    message = ""
    while(str[index] != '"'):
        quote += str[index]
        index += 1
        if index >= len(str):
            return Highlight("#FF0000",start, index, 'unclosed quotation')
    if lastBlockQuote.find(quote) != -1:
        return Highlight("#00FF00",start,index,"found in earlier block quote")
    endQuote = index
    gap = 0
    
    while(str[index] != '('):
        if(str[index] == '.' or str[index] == ','):
            color = "#FFFF00"
            message = "punctuation before citation"
            
        index += 1
        gap += 1
        if index >= len(str):
            return Highlight("#FF0000",start, endQuote, 'no citation found')
    startParen = index
    while(str[index] != ')'):
        if index >= len(str):
            return Highlight("#FF0000",startParen, len(str), 'Missing )')
        index += 1
    if gap > 20:
        return Highlight("#FFFF00", start, index, 'long gap between quote and citation')
    else:
        return Highlight(color, start, index, message)
        
   
    
def highlightBlock(str, index = 0):
    assert index < len(str)
    index += 1
    start = index
    assert index < len(str)
    block = ""
    while(str[index] != "("):
        block += str[index]
        index += 1
        if(index >= len(str)):
            return Highlight("#FF0000",start, index, "no citation found" )
    lastBlockQuote = block
    
    while(str[index] != ")"):
        index += 1
        if(index >= len(str)):
            return Highlight("#FF0000",start, index, "missing )" )
    return Highlight("#00FF00", start, index)
    
    
def highlightParenthesis(str, index = 0):
    return None

        



def highlight(str):
    highlights = []
    parensDepth = 0
    index = 0
    while index < len(str):
        if str[index] == '"':
            highlight = highlightQuote(str, index)
            highlights.append(highlight)
            index = highlight.end 
        elif str[index] == ':':
            highlight = highlightBlock(str, index)
            highlights.append(highlight)
            index = highlight.end 
        elif str[index] == '(':
            highlight = highlightParenthesis(str, index)
            if highlight is None:
                pass
            else:
                highlights.append(highlight)
                index = highlight.end 
    
        index += 1
            
            
        
        
    
    
    return highlights
    
"""
h = highlight(text)
print(h[0])"""
    
    
    
    