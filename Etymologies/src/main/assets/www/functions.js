function focus_on_initial_element_if_any(allowed_in_frame) {
     framebreak(allowed_in_frame)  // break out if someone is framing us

     elms = document.getElementsByClassName('initial_focus');
     if(!elms || !elms[0]) {
          return(false); 
     }
     elms[0].focus();
     return(true);
}

function framebreak(allowed_in_frame) {
   if (top != self) {
       if (!allowed_in_frame) {
         alert("This page is part of the Online Etymology Dictionary (http://www.etymonline.com/) The site you are on is showing this " +
               "page in a frameset, giving the appearance that it is their page.\n\n" +
               "This is not only a copyright infringement, and intellectually dishonest, but it raises " +
               "bandwidth usage on this end (and thus costs) without offering due credit or recompense.\n\n" +
               "Check out the real site.")
         top.location = self.location
         
       }
   }
}